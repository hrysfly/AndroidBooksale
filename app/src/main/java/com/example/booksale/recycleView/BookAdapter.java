package com.example.booksale.recycleView;

import android.app.usage.ExternalStorageStats;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksale.R;
import com.squareup.picasso.Picasso;

import java.io.Externalizable;
import java.io.File;
import java.net.URL;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private List<BookAttribute> mBookAttribute;
    private Context context;


    public BookAdapter(List<BookAttribute> mBookAttribute, Context context) {
        this.mBookAttribute = mBookAttribute;
        this.context = context;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView book_image;
        private TextView book_name;
        private TextView book_price;
        private TextView book_author;

        public ViewHolder(View itemView) {
            super(itemView);
            book_image = itemView.findViewById(R.id.recycle_item_image);
            book_name = itemView.findViewById(R.id.recycle_item_name);
            book_price = itemView.findViewById(R.id.recycle_item_price);
            book_author = itemView.findViewById(R.id.recycle_item_author);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.recycleviewitem_layout,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final BookAttribute bookAttribute = mBookAttribute.get(position);
        System.out.println("0");
        holder.book_image.setImageResource(bookAttribute.getBookImage());
        holder.book_author.setText(bookAttribute.getBookAuthor());
        holder.book_name.setText(bookAttribute.getBookName());
        holder.book_price.setText(bookAttribute.getBookPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getLayoutPosition();
                onItemClickListener.OnItemClick(holder.itemView,bookAttribute);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBookAttribute.size();
    }


    public interface OnItemClickListener {

        public void OnItemClick(View view, BookAttribute data);

    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

