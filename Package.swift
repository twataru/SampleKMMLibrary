// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "SampleKMMLibrary",
    targets: [
        .binaryTarget(
            name: "shared",
            path: "https://api.github.com/repos/wataru-taniuchi/sample-kmm-library/releases/tag/v1.0.0.zip"
        ),
    ]
)
