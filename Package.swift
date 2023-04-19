// swift-tools-version:5.3
import PackageDescription

let package = Package(
    name: "SampleKMMLibrary",
    platforms: [
        .iOS(.v13)
    ],
    products: [
        .library(
            name: "SampleKMMLibrary",
            targets: ["SampleKMMLibrary"]
        ),
    ],
    targets: [
        .binaryTarget(
            name: "SampleKMMLibrary",
            url: "https://api.github.com/repos/wataru-taniuchi/sample-kmm-library/releases/assets/v0.0.1/SampleKMMLibrary.xcframework.zip",
            checksum: "51dc258bf1fabf04575bf2b41ac1820be9dbcca2096db2a37cd9ad949a5c03c4"
        ),
    ]
)
