import {useEffect, useState} from "react";
import {Accelerometer} from "expo-sensors";

export const useAccelerometer = (interval = 200) => {
    const [data, setData] = useState({ x: 0, y: 0, z: 0 });

    useEffect(() => {
      Accelerometer.setUpdateInterval(interval);

      const subscription = Accelerometer.addListener(result => {
        setData(result);
      });

      return () => {
        subscription.remove();
      };
    }, [interval]);

    return data;
};