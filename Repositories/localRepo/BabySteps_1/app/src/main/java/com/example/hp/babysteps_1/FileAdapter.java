package com.example.hp.babysteps_1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.provider.MediaStore.Video.Thumbnails;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static java.util.Collections.*;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.MyViewHolder> {

   private Context ctx;
   private ArrayList<FilesV> fileList;

    public FileAdapter(Context ctx, ArrayList<FilesV> fileList) {
        this.ctx = ctx;
        this.fileList = fileList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater li = LayoutInflater.from(ctx);
        View view = li.inflate(R.layout.item_row,viewGroup,false);
        MyViewHolder mvh = new MyViewHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final FilesV filesV = fileList.get(i);
        Bitmap bmThumbnail;
        bmThumbnail = ThumbnailUtils.createVideoThumbnail(filesV.getName(),Thumbnails.MINI_KIND);
        myViewHolder.imageView.setImageBitmap(bmThumbnail);
        int j;
        for(j=filesV.getName().length()-1;filesV.getName().charAt(j)!='/';j--);
//        filesV.getName()
        String name = filesV.getName().substring(j+1);
//        Log.e("TAG",name);
//        myViewHolder.textView.setText(filesV.getName());

        myViewHolder.textView.setText(name);
        myViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ctx, Main2Activity.class);
                intent.putExtra("Key",filesV.getName());
                ctx.startActivity(intent);
            }
        });
       myViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent= new Intent(ctx, Main2Activity.class);
               intent.putExtra("Key",filesV.getName());
               ctx.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.iView);
        }
    }
}
