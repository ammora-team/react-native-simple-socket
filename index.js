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

function convertError(error) {
  if (!error) {
    return null;
  }
  const out = new Error(error.message);
  // $FlowFixMe: adding custom properties to error.
  out.key = error.key;
  return out;
}

export default Socket
