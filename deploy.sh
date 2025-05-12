#!/bin/bash
export SRV_PASS
sshpass -p $SRV_PASS scp target/HC-1.0-SNAPSHOT.jar serwer@192.168.1.46:/home/serwer/minecraft/paper/plugins