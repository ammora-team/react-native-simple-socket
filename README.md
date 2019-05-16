
# @ammora/react-native-simple-socket

## Getting started

`$ npm install @ammora/react-native-simple-socket --save`

### Mostly automatic installation

`$ react-native link @ammora/react-native-simple-socket`

### Manual installation

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNSimpleSocketPackage;` to the imports at the top of the file
  - Add `new RNSimpleSocketPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':@ammora_react-native-simple-socket'
  	project(':@ammora_react-native-simple-socket').projectDir = new File(rootProject.projectDir, 	'../node_modules/@ammora/react-native-simple-socket/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':@ammora_react-native-simple-socket')
  	```

## Usage
```javascript
import RNSimpleSocket from '@ammora/react-native-simple-socket';

// TODO: What to do with the module?
RNSimpleSocket;
```
  