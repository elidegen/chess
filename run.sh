#!/bin/bash
set -e

value=$1

echo "🛠️  Compiling Scala project..."
sbt compile

echo "🚀 Building $value x $value Chessboard..."
sbt "run $value"