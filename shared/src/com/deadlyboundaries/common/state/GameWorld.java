package com.deadlyboundaries.common.state;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.deadlyboundaries.common.network.constants.GameConstant;
import com.deadlyboundaries.common.network.register.dto.EnemyCollisionDto;
import com.deadlyboundaries.common.network.register.dto.NewEnemyDto;
import com.deadlyboundaries.common.network.register.dto.PlayerUpdateDto;
import com.deadlyboundaries.common.network.register.dto.PlayersBaseDto;
import com.deadlyboundaries.common.network.register.dto.SpawnPointDto;
import com.deadlyboundaries.common.utils.MovementUtils;
import com.deadlyboundaries.common.model.entities.Drawable;
import com.deadlyboundaries.common.model.entities.dynamic.allies.Player;
import com.deadlyboundaries.common.model.entities.level.Wall;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import space.earlygrey.shapedrawer.ShapeDrawer;

@Data
@Slf4j
public class GameWorld implements Drawable {

    private LocalGameState localGameState;
    private Level level;

    public GameWorld() {
        this.localGameState = new LocalGameState(new HashMap<>(), new ConcurrentHashMap<>(),
                new ConcurrentHashMap<>());
//        World<Item>
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        level.drawMe(shapeDrawer);
        localGameState.drawMe(shapeDrawer);
    }

    public void updatePlayer(PlayerUpdateDto playerUpdateDto) {
        log.debug("updating player: {}", playerUpdateDto.toString());
        localGameState.updateUsingPlayerList(playerUpdateDto);
    }

    public void updatePlayerBase(PlayersBaseDto playersBaseDto) {
        level.updateUsingPlayerBase(playersBaseDto);
    }

    public void updateSpawnPoints(SpawnPointDto spawnPointDto) {
        level.updateUsingSpawnPoints(spawnPointDto);
    }

    public void updateEnemyCollision(EnemyCollisionDto enemyCollisionDto) {
        localGameState.updateUsingEnemyCollision(enemyCollisionDto);
    }

    public void updateNewEnemy(NewEnemyDto newEnemyDto) {
        localGameState.updateNewEnemy(newEnemyDto);
    }

    public void interpolatePlayerPositions(float delta) {
        MovementUtils.interpolatePlayers(localGameState.getPlayersList(), delta);
    }

    public void moveEnemies(float delta) {
        localGameState.getEnemiesList().forEach(e -> MovementUtils.moveEnemy(e, delta));
    }

    public void moveBullets(float delta) {
        localGameState.getPlayersList().forEach(p -> p.updateProjectiles(delta, level));
    }

    public void moveEntities(float delta) {
        moveBullets(delta);
        interpolatePlayerPositions(delta);
        moveEnemies(delta);
    }

    public Vector2 randomPos() {
        final int offset = 2 * GameConstant.PIXELS_PER_GRID_CELL;
        float x = MathUtils.random(offset, GameConstant.SIZE_X - offset);
        float y = MathUtils.random(offset, GameConstant.SIZE_Y - offset);
        return new Vector2(x, y);
    }

    // TODO: 2020-07-01 Fix wall teleportation
    public void checkForPlayerCollisionWithWalls() {
        for (Player p : localGameState.getPlayersList()) {
            float px = p.getCurrCenterX();
            float pxRight = px + p.getHalfSize();
            float pxLeft = px - p.getHalfSize();
            float py = p.getCurrCenterY();
            float pyTop = py + p.getHalfSize();
            float pyBottom = py - p.getHalfSize();
            Vector2 pDest = p.getDestination();
            for (Wall w : level.getWalls()) {
                Rectangle wallRect = w.getRectangle();
                if (wallRect.overlaps(p.getShape().getBoundingRectangle())) {
                    if (pyTop > wallRect.y
                            && pyBottom < wallRect.y
                            && px > wallRect.x
                            && px < wallRect.x + wallRect.width) {
                        //  PLayer up collision with wall
                        pDest.x = px;
                        pDest.y = pyBottom - 1;
                    } else if (pyBottom < wallRect.y + wallRect.height
                            && px > wallRect.x
                            && px < wallRect.x + wallRect.width) {
                        // player bottom collision with wall
                        pDest.x = px;
                        pDest.y = pyTop + 1;
                    } else if (wallRect.x + wallRect.width >= pxRight
                            && py > wallRect.y
                            && py < wallRect.y + wallRect.height) {
                        // Player right collision with wall
                        pDest.x = pxLeft - 1;
                        pDest.y = py;
                    } else if (wallRect.x <= pxLeft
                            && py > wallRect.y
                            && py < wallRect.y + wallRect.height) {
                        // Player left collision with wall
                        pDest.x = pxRight + 1;
                        pDest.y = py;
                    }
                }
            }
        }
    }

    /**
     * An attempt to fix wall teleportation Checks if a line intersects with any of the walls, and
     * returns the point on the closest wall it intersects with.
     *
     * @param p1
     * @param p2
     * @return the intersection point between the line and the closest wall from p1
     */
    public Optional<Vector2> checkForLineIntersectionWithAllWalls(Vector2 p1, Vector2 p2) {
        return level.getWalls().stream()
                .flatMap(w -> isLineIntersectingWithWall(w, p1, p2))
                .reduce((v1, v2) -> p1.dst2(v2) < p1.dst(v1) ? v2 : v1);
    }

    private Stream<Vector2> isLineIntersectingWithWall(Wall wall, Vector2 p1, Vector2 p2) {
        return getLinesFromWalls(wall)
                .map(line -> getIntersectionPointIfIntersection(line, p1, p2))
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    private Optional<Vector2> getIntersectionPointIfIntersection(Vector2[] line, Vector2 p1,
            Vector2 p2) {
        Vector2 intersectionPoint = new Vector2();
        if (Intersector.intersectLines(line[0], line[1], p1, p2, intersectionPoint)) {
            log.debug("Intersection found between player at {} going to {} with line {} {}",
                    p1.toString(), p2.toString(), line[0].toString(), line[1].toString());
            return Optional.of(intersectionPoint);
        }
        return Optional.empty();
    }

    private Stream<Vector2[]> getLinesFromWalls(Wall wall) {
        Rectangle wallRect = wall.getRectangle();
        Vector2 bl, br, tl, tr;
        bl = new Vector2(wallRect.x, wallRect.y);
        br = new Vector2(wallRect.x + wallRect.width, wallRect.y);
        tl = new Vector2(wallRect.x + wallRect.width, wallRect.y + wallRect.height);
        tr = new Vector2(wallRect.x, wallRect.y + wallRect.height);
        return Stream.of(
                new Vector2[]{bl, br},
                new Vector2[]{br, tr},
                new Vector2[]{tr, tl},
                new Vector2[]{tl, bl}
        );
    }

}
