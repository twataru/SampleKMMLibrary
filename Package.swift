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
         .target(name: "SampleKMMLibrary", path: "shared/swiftPM"),
    ]
)
