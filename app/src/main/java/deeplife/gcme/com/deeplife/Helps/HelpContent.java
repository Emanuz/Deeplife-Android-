package deeplife.gcme.com.deeplife.Helps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import deeplife.gcme.com.deeplife.Activities.Login;
import deeplife.gcme.com.deeplife.R;

/**
 * Created by BENGEOS on 2/19/16.
 */
public class HelpContent extends Fragment {
    private int isStart,isEnd;
    private String Title;
    private int Image;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Title = getArguments().getString("Title");
        isStart = getArguments().getInt("isStart");
        isEnd = getArguments().getInt("isEnd");
        Image = getArguments().getInt("Image");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.help_page, container, false);
        TextView Help_Title = (TextView) viewGroup.findViewById(R.id.help_title);
        Button Finish = (Button) viewGroup.findViewById(R.id.help_finish);
        ImageView Help_Image = (ImageView) viewGroup.findViewById(R.id.help_image);
        Finish.setVisibility(View.INVISIBLE);
        if(isEnd == 1){
            Finish.setVisibility(View.VISIBLE);
        }
        Help_Image.setBackgroundResource(Image);
        Help_Image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Help_Title.setText(Title);
        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        return viewGroup;
    }
}
