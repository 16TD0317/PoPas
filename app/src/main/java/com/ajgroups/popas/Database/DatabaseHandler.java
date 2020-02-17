package com.ajgroups.popas.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseHandler {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    public int addUsers()
    {
        return 1;
    }
}
