package com.sahiti.usf.utils;

import java.io.*;
import java.util.List;

public class DeepCopyUtils {

    public static <T extends Serializable> List<T> deepCopy(List<T> original) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(original);
            objectOutputStream.flush();

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            List<T> deepCopy = (List<T>) objectInputStream.readObject();
            return deepCopy;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }
}