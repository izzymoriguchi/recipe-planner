package edu.sjsu.izzymoriguchi.myapplication;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class RetainedFragment extends Fragment {
    // a bundle object we want to retain
    private Bundle mydata;

    public RetainedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_retained, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the retain instance flag
        setRetainInstance(true);
    }

    public void setBundle(Bundle data) {
        this.mydata = data;
    }

    public Bundle getBundle() {
        return mydata;
    }

}
