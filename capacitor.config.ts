import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'com.deencommerce.app',
  appName: 'DEEN Commerce',
  webDir: 'www',
  server: {
    url: 'https://deencommerce.com/',
    cleartext: false,
    allowNavigation: [
      'deencommerce.com',
      '*.deencommerce.com',
    ],
  },
  android: {
    allowMixedContent: false,
    captureInput: true,
    backgroundColor: '#FFFFFF',
  },
  plugins: {
    SplashScreen: {
      launchShowDuration: 2000,
      launchAutoHide: true,
      backgroundColor: '#FFFFFF',
      androidSplashResourceName: 'splash',
      showSpinner: false,
    },
    StatusBar: {
      overlaysWebView: true,
      style: 'DARK',
      backgroundColor: '#00000000',
    },
  },
};

export default config;
