package com.example.booksale.recycleView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksale.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {





    private List<CartAttribute> mcartAttributes;
    private Context context;




    public CartAdapter(List<CartAttribute> mcartAttributes, Context context) {
        this.mcartAttributes = mcartAttributes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.cart_child_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final CartAttribute cartAttribute = mcartAttributes.get(position);
        holder.itemView.setTag(position);
        holder.book_image.setImageResource(cartAttribute.getBookImage());
        holder.book_price.setText(cartAttribute.getBookprice()+"");
        holder.book_count.setText(cartAttribute.getCount()+"");
        holder.book_name.setText(cartAttribute.getBookname());
        holder.book_checkbox.setChecked(cartAttribute.isCheck());
        /*holder.checkall.setChecked(false);*/
        holder.book_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getLayoutPosition();
               Boolean b= holder.book_checkbox.isChecked();
                onCheckboxClickListener.OnCheckboxClick(holder.itemView,cartAttribute,b);
            }
        });

        holder.book_countadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getLayoutPosition();
                onNumAddClickListener.OnNumAddClick(holder.itemView,cartAttribute);
            }
        });


        holder.book_countreduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getLayoutPosition();
                onNumReduceClickListener.OnNumReduceClick(holder.itemView,cartAttribute);
            }
        });

        /*holder.checkall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkall.isChecked()){
                    for (int i=0;i<mcartAttributes.size();i++)
                    {
                        mcartAttributes.get(i).setCheck(true);
                    }
                }else {
                    for (int i=0;i<mcartAttributes.size();i++)
                    {
                        mcartAttributes.get(i).setCheck(false);
                    }
                }
                notifyDataSetChanged();
            }
        });*/

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mcartAttributes.size();
    }




    public interface OnCheckboxClickListener {

        public void OnCheckboxClick(View view, CartAttribute data,Boolean flag);

    }

    //需要外部访问，所以需要设置set方法，方便调用
    private CartAdapter.OnCheckboxClickListener onCheckboxClickListener;

    public void setOnItemClickListener(CartAdapter.OnCheckboxClickListener onCheckboxClickListener) {
        this.onCheckboxClickListener = onCheckboxClickListener;
    }

    /*加*/

    public interface OnNumAddClickListener {

        public void OnNumAddClick(View view, CartAttribute data);

    }

    //需要外部访问，所以需要设置set方法，方便调用
    private CartAdapter.OnNumAddClickListener onNumAddClickListener;

    public void setOnNumAddClickListener(CartAdapter.OnNumAddClickListener onNumAddClickListener) {
        this.onNumAddClickListener = onNumAddClickListener;
    }


    /*减*/

    public interface OnNumReduceClickListener {

        public void OnNumReduceClick(View view, CartAttribute data);

    }

    //需要外部访问，所以需要设置set方法，方便调用
    private CartAdapter.OnNumReduceClickListener onNumReduceClickListener;

    public void setOnNumReduceClickListener(CartAdapter.OnNumReduceClickListener onNumReduceClickListener) {
        this.onNumReduceClickListener = onNumReduceClickListener;
    }




    public  class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView book_image;
        private TextView book_name;
        private TextView book_price;
        private TextView book_count;
        private CheckBox book_checkbox;
        private TextView book_countadd;
        private TextView book_countreduce;
       /* private CheckBox checkall;*/


        public ViewHolder(View itemView) {
            super(itemView);
            book_image = itemView.findViewById(R.id.child_img);
            book_checkbox = itemView.findViewById(R.id.child_check);
            book_count = itemView.findViewById(R.id.child_num);
            book_name = itemView.findViewById(R.id.child_name);
            book_price = itemView.findViewById(R.id.child_price);
            book_countadd = itemView.findViewById(R.id.child_jia);
            book_countreduce = itemView.findViewById(R.id.child_jian);
            /*checkall = itemView.findViewById(R.id.main_check_all);*/
        }
    }
}
