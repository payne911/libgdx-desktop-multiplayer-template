Some useful commands:

* ``docker logs -f $(docker ps -qf "name=deadly")`` : finds the container ID of a container which contains the word "deadly" in its container name, and then launches a real-time following of its internal logs.