import { useState, useEffect } from 'react';
import * as Battery from 'expo-battery';

export const useBatteryLevel = () => {
  const [level, setLevel] = useState(0);

  useEffect(() => {
    let subscription: Battery.Subscription;

    const getInitialAndSubscribe = async () => {
      const currentLevel = await Battery.getBatteryLevelAsync();
      setLevel(currentLevel);

      subscription = Battery.addBatteryLevelListener(({ batteryLevel }) => {
        setLevel(batteryLevel);
      });
    };

    getInitialAndSubscribe();

    return () => {
      if (subscription) subscription.remove();
    };
  }, []);

  return level;
};