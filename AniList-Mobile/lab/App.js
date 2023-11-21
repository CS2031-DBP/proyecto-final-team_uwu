import React from 'react';
import { StatusBar } from 'expo-status-bar';
import { SafeAreaView, StyleSheet, Text, View } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { Header1 } from './src/components/Header/Header1';
import { Home } from './src/components/views/Home';
import { ProfileUser } from './src/components/views/ProfileUser';
import AnimeDetails from './src/components/views/AnimeDetails';
const Stack = createStackNavigator();
const routeScreenDefaultOptions = {
  headerStyle:{
    backgroundColor: 'rgb(11, 22, 34)',

  },
  headerTitleStyle:{
    color:"#fff"
  },

}
export default function App() {
  return (
    <NavigationContainer>
      <StatusBar style="auto" />
      <SafeAreaView style={styles.container}>
        <Header1/>
        <Stack.Navigator>
          <Stack.Screen name="Home" component={Home} options={routeScreenDefaultOptions} />
          <Stack.Screen name="AnimeDetails" component={AnimeDetails} options={routeScreenDefaultOptions}/>
          <Stack.Screen name="ProfileUser" component={ProfileUser} options={routeScreenDefaultOptions} />
        </Stack.Navigator>
      </SafeAreaView>
    </NavigationContainer>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'rgb(11, 22, 34)',
  },
});
