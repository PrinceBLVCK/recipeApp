package com.example.recipeapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentAddRecipe extends Fragment {

    private onFragmentSelector listener;
    Button add_btn;
    EditText title, ingredient, instructions;
    Uri imgUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);


        add_btn = view.findViewById(R.id.add_btn);
        title = view.findViewById(R.id.title_edit_text);
        ingredient = view.findViewById(R.id.ingredients);
        instructions = view.findViewById(R.id.instructions);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Title = title.getText().toString();
                String Ingredient = ingredient.getText().toString();
                String Instruction = instructions.getText().toString();

                listener.onAddButtonClicked(Title, Ingredient, Instruction);
            }
        });

        CircleImageView img = view.findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgUri = listener.add_img();
                img.setImageURI(imgUri);
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof onFragmentSelector) {
            listener = (onFragmentSelector) context;
        }
        else {
            throw new ClassCastException(context.toString() + " must implement listener");
        }
    }

    public  interface onFragmentSelector {
        public void onAddButtonClicked(String title, String ingredients, String instruction);

        public Uri add_img();
    }
}


