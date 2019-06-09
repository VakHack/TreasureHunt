package com.example.roees.treasurehunt;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

public class FirebaseServerHandler {
    private FirebaseAuth serverAuth;
    private boolean isSucceeded;
    private FirebaseStorage storage;
    private Serializable retrievedData;
    private String logFeedback;
    private String storageFeedback;

    public FirebaseServerHandler() {
        serverAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageFeedback = logFeedback = "No feedback";
    }
    public String getLogFeedback() {
        return logFeedback;
    }
    public String getStorageFeedback() {
        return storageFeedback;
    }
    public boolean didActionSucceeded() {
        return isSucceeded;
    }
    public boolean tryRegister(String username, String password) {
        serverAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()) {
                isSucceeded = true;
                logFeedback = "Register Successfully";
            } else {
                isSucceeded = false;
                logFeedback = task.getException().getMessage();
            }
            }
        });
        return isSucceeded;
    }
    public boolean tryLogin(String username, String password) {
        serverAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()) {
                isSucceeded = true;
                logFeedback = "Logged in Successfully";
            } else {
                isSucceeded = false;
                logFeedback = task.getException().getMessage();
            }
            }
        });
        return isSucceeded;
    }
    public boolean tryUploadData(Serializable data) {
        byte[] serializedData = SerializationUtils.serialize(data);
        UploadTask uploadTask = storage.getReference().child("uploads/" + getServerUID()).putBytes(serializedData);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            isSucceeded = true;
            storageFeedback = "Data uploaded successfully";
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            isSucceeded = false;
            storageFeedback = e.getMessage();
            }
        });
        return isSucceeded;
    }
    public void tryRetrieveData(String uid) {
        final long ONE_MEGABYTE = 1024 * 1024;
        StorageReference storageReference = storage.getReference().child("uploads/" + uid);
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
            retrievedData = SerializationUtils.deserialize(bytes);
            storageFeedback = "Data downloaded successfully";
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            retrievedData = null;
            storageFeedback = exception.getMessage();
            }
        });
    }
    public Serializable getRetrievedData() {
        return retrievedData;
    }
    public String getServerUID() {
        return serverAuth.getCurrentUser().getUid();
    }
}
