import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, Image, Alert ,TextInput } from 'react-native';
import * as ImagePicker from 'expo-image-picker';
import AsyncStorage from '@react-native-async-storage/async-storage';

export const ProfileUser = () => {
  const [image, setImage] = useState(null);                 // imagen de perfil
  const [username, setUsername] = useState('Usuario1');     // nombre de usuario
  const [isEditing, setIsEditing] = useState(false);        // indica si el nombre de usuario se está editando

  // solicita permiso para acceder a la cámara y la galería
  useEffect(() => {
    (async () => {
      const cameraPermission = await ImagePicker.requestCameraPermissionsAsync();
      const mediaPermission = await ImagePicker.requestMediaLibraryPermissionsAsync();
      // si no se otorga permiso para acceder a la cámara y la galería, muestra una alerta
      if (cameraPermission.status !== 'granted' || mediaPermission.status !== 'granted') {
        alert('Se necesitan permisos para acceder a la cámara y la galería.');
      }
    })();
  }, []);

  // guarda la imagen en el almacenamiento local
  const saveImage = async (imageUri) => {
    try {
      await AsyncStorage.setItem('profileImage', imageUri);
    } catch (error) {
      console.log('Error saving image:', error);
    }
  };
  // carga la imagen del almacenamiento local
  const loadImage = async () => {
    try {
      const storedImage = await AsyncStorage.getItem('profileImage');
      if (storedImage !== null) {
        setImage(storedImage);
      }
    } catch (error) {
      console.log('Error loading image:', error);
    }
  };
  // carga el nombre de usuario del almacenamiento local
  const loadText = async () => {
    try {
      const storedText = await AsyncStorage.getItem('username');
      if (storedText !== null) {
        setUsername(storedText);
      }
    } catch (error) {
      console.log('Error loading image:', error);
    }
  };
  // carga la imagen y el nombre de usuario al iniciar la aplicación
  useEffect(() => {
    loadImage();
    loadText();
  }, []);
  // abre la galería y permite seleccionar una imagen
  const pickImageFromLibrary = async () => {
    let result = await ImagePicker.launchImageLibraryAsync({
      mediaTypes: ImagePicker.MediaTypeOptions.Images,
      allowsEditing: true,
      aspect: [1, 1],
      quality: 1,
    });

    handleImagePickerResult(result);
  };
  // abre la cámara y permite tomar una foto
  const pickImageFromCamera = async () => {
    // solicita permiso para acceder a la cámara
    let result = await ImagePicker.launchCameraAsync({
      allowsEditing: true,
      aspect: [1, 1],
      quality: 1,
    });
    // maneja el resultado de la selección de imagen
    handleImagePickerResult(result);
  };

  // maneja el resultado de la selección de imagen
  const handleImagePickerResult = (result) => {
    if (result && !result.canceled) {
      if (result.assets && result.assets.length > 0) {
        setImage(result.assets[0].uri);
        saveImage(result.assets[0].uri);
      } else {
        console.log('No assets found in the result');
      }
    } else {
      console.log('Cancelled or result is null');
    }
  };

  // muestra las opciones de selección de imagen
  const showImagePickerOptions = () => {
    Alert.alert('Seleccionar imagen', 'Seleccione una opción', [
      {
        text: 'Galería',
        onPress: pickImageFromLibrary,
      },
      {
        text: 'Cámara',
        onPress: pickImageFromCamera,
      },
      {
        text: 'Cancelar',
        style: 'cancel',
      },
    ]);
  };

  // guarda el nombre de usuario en el estado y en el almacenamiento local
  const handleOnSubmit = () => {
    setIsEditing(false);
    if (username.trim() !== '') {
      setUsername(username);
      AsyncStorage.setItem('username', username).catch((error) => console.log('Error setting username:', error));
    }
  };

  return (
    <View style={styles.container}>
      <View style={styles.profileContent}>
        <View style={[styles.circle, image ? { backgroundColor: 'transparent' } : null]}>
          {image && <Image source={{ uri: image }} style={styles.circle} />}
        </View>
        <TouchableOpacity onPress={() => setIsEditing(true)}>
          {isEditing ? (
            <TextInput
              style={styles.username}
              value={username}
              onChangeText={(text) => setUsername(text)}
              onBlur={handleOnSubmit}
              autoFocus={true}
            />
          ) : (
            <Text style={styles.username}>{username}</Text>
          )}
        </TouchableOpacity>
      </View>
      <TouchableOpacity style={styles.uploadButton} onPress={showImagePickerOptions}>
        <Text style={styles.uploadText}>Upload Image</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'rgb(229,235,241)',
  },
  profileContent: {
    alignItems: 'center',
  },
  circle: {
    width: 300,
    height: 300,
    borderRadius: 150,
    backgroundColor: 'black',
    marginBottom: 20,
  },
  username: {
    fontSize: 20,
    color: 'black',
    marginBottom: 10,
    fontWeight: 'bold',

  },
  uploadButton: {
    backgroundColor: 'skyblue',
    padding: 10,
    borderRadius: 10,
    marginTop: 20,
  },
  uploadText: {
    fontSize: 18,
    color: 'white',
    textAlign: 'center',
  },
});
