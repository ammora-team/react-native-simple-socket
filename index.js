import { NativeModules } from 'react-native';

const { RNSimpleSocket } = NativeModules;

const Socket = {
  send: function(hostname, port, data) {
    return new Promise((resolve, reject) => {
      RNSimpleSocket.send(hostname, port, data, function(error) {
        const err = convertError(error);
        if (err) {
          reject(err);
        } else {
          resolve(null);
        }
      });
    });
  }
}

export default Socket

