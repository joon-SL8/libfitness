#!/bin/bash
# This is a comment, ignored by the shell
echo "exporting xcframework"

if [ ! -d "./libfitness/build/XCFrameworks/release/libfitness.xcframework" ]; then
    echo "XCFramework does not exist"
    exit 1
fi

rm -rf ../apps/ios/sdk/libfitness.xcframework
cp -R libfitness/build/XCFrameworks/release/libfitness.xcframework ../apps/ios/sdk/

