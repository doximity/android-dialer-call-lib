# Changelog

All notable changes to this project will be documented in this file.

## [Unreleased]

## [2.0.0] - 2025-11-10

### Added
- New `startVoiceCall()` function to immediately initiate a voice call through Doximity Dialer
- New `startVideoCall()` function to immediately initiate a video call through Doximity Dialer
- Kotlin support - library now written in Kotlin
- Return type documentation for all functions (Boolean indicating success/failure)

### Changed
- Complete rewrite of library in Kotlin (Java interop maintained)
- **BREAKING**: Minimum SDK version updated from 19 to 21
- **BREAKING**: Target SDK version updated to 35
- **BREAKING**: Compile SDK version updated to 36
- **BREAKING**: JVM target updated from 11 to 17
- Sample app rewritten in Kotlin with Jetpack Compose
- Sample app updated to use Material Design 3
- Build configuration migrated from Groovy to Kotlin DSL (`.gradle.kts`)
- Updated Play Store redirect to use Singular marketing URL (replacing AppsFlyer)
- Updated all documentation and README with Kotlin examples as primary language

### Technical
- Updated to androidx.core:core-ktx:1.17.0
- Updated to androidx.appcompat:appcompat:1.6.1
- Added Jetpack Compose BOM 2024.12.01
- Added Material Icons Extended for Compose
- Updated Kotlin to 2.1.21
- Updated Android Gradle Plugin to 8.12.1
- Modernized Gradle configuration (removed deprecated syntax)

## [1.1.1] - 2023-12-13

### Changed
- Updated library version to 1.1.1 for release consistency
- Updated Maven publication versions

## [1.1.0] - 2023-12-07

### Changed
- Updated Doximity app package name from `com.doximity.doxdialer` to `com.doximity.doximitydroid`
- Updated to target SDK 33
- Updated to androidx dependencies
- Improved Android 11+ package visibility handling
- Updated version code to 2

### Fixed
- Compatibility with Android 11 package visibility changes

## [1.0.0] - 2018-XX-XX

### Added
- Initial release of CallWithDoxDialer library
- `dialPhoneNumber()` function to launch Doximity Dialer with prefilled phone number
- Support for multiple phone number formats (plain, formatted, international)
- Automatic Play Store redirect when Doximity Dialer is not installed
- AppsFlyer marketing URL integration for Play Store links
- Doximity Dialer icon drawables for use in apps
- Sample app demonstrating library usage
- Support for Android API 19+

[Unreleased]: https://github.com/doximity/android-dialer-call-lib/compare/v2.0.0...HEAD
[2.0.0]: https://github.com/doximity/android-dialer-call-lib/compare/v1.1.1...v2.0.0
[1.1.1]: https://github.com/doximity/android-dialer-call-lib/compare/v1.1...v1.1.1
[1.1.0]: https://github.com/doximity/android-dialer-call-lib/compare/v1.0...v1.1
[1.0.0]: https://github.com/doximity/android-dialer-call-lib/releases/tag/v1.0
