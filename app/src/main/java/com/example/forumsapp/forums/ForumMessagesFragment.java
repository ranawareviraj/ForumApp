package com.example.forumsapp.forums;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.forumsapp.databinding.FragmentForumMessagesBinding;
import com.example.forumsapp.databinding.MessageRowItemBinding;
import com.example.forumsapp.models.Auth;
import com.example.forumsapp.models.Forum;
import com.example.forumsapp.models.Message;

import com.example.forumsapp.utils.Constansts;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ForumMessagesFragment extends Fragment {
    private Forum mForum;
    private Auth mAuth;
    ArrayList<Message> messages = new ArrayList<>();

    public ForumMessagesFragment() {
        // Required empty public constructor
    }

    public static ForumMessagesFragment newInstance(Forum forum, Auth auth) {
        ForumMessagesFragment fragment = new ForumMessagesFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constansts.ARG_PARAM_FORUM, forum);
        args.putSerializable(Constansts.ARG_PARAM_AUTH, auth);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mForum = (Forum) getArguments().getSerializable(Constansts.ARG_PARAM_FORUM);
            mAuth = (Auth) getArguments().getSerializable(Constansts.ARG_PARAM_AUTH);
        }
    }

    FragmentForumMessagesBinding binding;
    MessagesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentForumMessagesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(Constansts.FORUM_MESSAGES_FRAGMENT_TITLE);

        binding.textViewForumCreatedAt.setText(mForum.getCreated_at());
        binding.textViewForumCreatorName.setText(mForum.getCreatedByFname() + " " + mForum.getCreatedByLname());
        binding.textViewForumTitle.setText(mForum.getTitle());


        adapter = new MessagesAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        getMessages();
        binding.buttonSubmit.setOnClickListener(view1 -> {
            String message = binding.editTextMessage.getText().toString();
            binding.editTextMessage.setText("");
            if (message.isEmpty()) {
                Toast.makeText(getContext(), "Message is required", Toast.LENGTH_SHORT).show();
            } else {
                RequestBody formBody = new FormBody.Builder()
                        .add(Constansts.THREAD_ID, mForum.getThread_id())
                        .add(Constansts.MESSAGE, message)
                        .build();
                Request request = new Request.Builder()
                        .url(Constansts.ADD_MESSAGE_URL)
                        .addHeader(Constansts.AUTHORIZATION, "BEARER " + mAuth.getToken())
                        .post(formBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response)
                            throws IOException {
                        if (response.isSuccessful()) {
                            getActivity().runOnUiThread(() -> getMessages());
                        } else {

                        }
                    }
                });
            }
        });
    }

    void getMessages() {
        Request request = new Request.Builder()
                .url(Constansts.GET_MESSAGES_URL + mForum.getThread_id())
                .addHeader("Authorization", "BEARER " + mAuth.getToken())
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
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        JSONArray jsonArrayMsgs = jsonObject.getJSONArray("messages");
                        messages.clear();

                        for (int i = 0; i < jsonArrayMsgs.length(); i++) {
                            JSONObject msgJsonObject = jsonArrayMsgs.getJSONObject(i);
                            Message message = new Message(msgJsonObject);
                            messages.add(message);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                } else {

                }
            }
        });


    }


    class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {

        @NonNull
        @Override
        public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MessagesViewHolder(MessageRowItemBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
            Message message = messages.get(position);
            holder.setupUI(message);
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        class MessagesViewHolder extends RecyclerView.ViewHolder {
            Message mMessage;
            MessageRowItemBinding mBinding;

            public MessagesViewHolder(MessageRowItemBinding mBinding) {
                super(mBinding.getRoot());
                this.mBinding = mBinding;
            }

            void setupUI(Message message) {
                this.mMessage = message;
                mBinding.textViewMessage.setText(message.getMessage());
                mBinding.textViewMessageCreatedAt.setText(message.getCreated_at());
                mBinding.textViewMessageCreatorName.setText(message.getCreatedByFname() + " " + message.getCreatedByLname());

                if (mAuth.getUser_id().equals(mMessage.getCreatedByUserId())) {
                    mBinding.imageViewDeleteMessage.setVisibility(View.VISIBLE);
                    mBinding.imageViewDeleteMessage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Request request = new Request.Builder()
                                    .url("https://www.theappsdr.com/api/message/delete/" + mMessage.getMessage_id())
                                    .addHeader("Authorization", "BEARER " + mAuth.getToken())
                                    .build();

                            client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                    if (response.isSuccessful()) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                getMessages();
                                            }
                                        });
                                    } else {

                                    }
                                }
                            });

                        }
                    });

                } else {
                    mBinding.imageViewDeleteMessage.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

}