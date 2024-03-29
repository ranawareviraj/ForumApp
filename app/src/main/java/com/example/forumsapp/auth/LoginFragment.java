package com.example.forumsapp.auth;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.forumsapp.databinding.FragmentLoginBinding;
import com.example.forumsapp.models.Auth;

import com.example.forumsapp.utils.Constansts;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }

    FragmentLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    private final OkHttpClient client = new OkHttpClient();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(Constansts.LOGIN_FRAGMENT_TITLE);

        binding.editTextEmail.setText("");
        binding.editTextPassword.setText("");
        binding.buttonCreateNewAccount.setOnClickListener(view1 -> mListener.gotoRegister());
        binding.buttonLogin.setOnClickListener(view2 -> {
            String email = binding.editTextEmail.getText().toString();
            String password = binding.editTextPassword.getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Please enter email and password", Toast.LENGTH_SHORT).show();
            } else {
                //perform the login ..
                RequestBody formBody = new FormBody.Builder()
                        .add(Constansts.EMAIL, email)
                        .add(Constansts.PASSWORD, password)
                        .build();
                Request request = new Request.Builder()
                        .url(Constansts.LOGIN_API_URL)
                        .post(formBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String body = response.body().string();
                            Log.d("demo", "onResponse: " + body);
                            try {
                                JSONObject jsonObject = new JSONObject(body);
                                Auth auth = new Auth(jsonObject);

                                getActivity().runOnUiThread(() -> mListener.authSuccessful(auth));
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                                    "Unable to login !!", Toast.LENGTH_SHORT).show());
                        }
                    }
                });
            }
        });
    }

    LoginListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof LoginListener) {
            mListener = (LoginListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement LoginListener");
        }
    }

    public interface LoginListener {
        void authSuccessful(Auth auth);

        void gotoRegister();
    }
}