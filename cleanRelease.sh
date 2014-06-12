#!/bin/bash
mvn release:rollback
mvn release:clean
echo "Done."
