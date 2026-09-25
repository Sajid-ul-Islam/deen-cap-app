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
    backgroundColor: '#0A4D3C',
  },
  plugins: {
    SplashScreen: {
      launchShowDuration: 2500,
      launchAutoHide: true,
      backgroundColor: '#0A4D3C',
      androidSplashResourceName: 'splash',
      showSpinner: false,
    },
    StatusBar: {
      backgroundColor: '#063328',
    },
  },
};

export default config;
