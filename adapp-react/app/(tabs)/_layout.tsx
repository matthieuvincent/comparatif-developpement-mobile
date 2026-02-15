import { Tabs } from 'expo-router';
import React from 'react';
import { IconSymbol } from '@/components/ui/icon-symbol';
import { Labels } from "@/constants/labels";

export default function TabLayout() {

  return (
      <Tabs
          screenOptions={{
              headerShown: false,
          }}>
          <Tabs.Screen
              name="index"
              options={{
                  title: Labels.device.title.visual,
                  tabBarIcon: ({ color }) => <IconSymbol size={28} name="iphone.gen3" color={color} />,
              }}
          />
          <Tabs.Screen
              name="qrcode"
              options={{
                  title: Labels.qrCode.title.visual,
                  tabBarIcon: ({ color }) => <IconSymbol size={28} name="qrcode" color={color} />,
              }}
          />
          <Tabs.Screen
              name="performance"
              options={{
                  title: Labels.performance.title.visual,
                  tabBarButtonTestID: 'performance_tab',
                  tabBarIcon: ({ color }) => <IconSymbol size={28} name="gauge.with.dots.needle.33percent" color={color} />,
              }}
          />
      </Tabs>
  );
}