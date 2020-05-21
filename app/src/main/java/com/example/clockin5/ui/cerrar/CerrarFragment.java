package com.example.clockin5.ui.cerrar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.clockin5.R;
import com.example.clockin5.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class CerrarFragment extends Fragment {

    private CerrarViewModel cerrarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        cerrarViewModel =
                ViewModelProviders.of(this).get(CerrarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cerrar, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);
        return root;
    }
}
