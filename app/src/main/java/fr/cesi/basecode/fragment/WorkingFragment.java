package fr.cesi.basecode.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;

import fr.cesi.base.controllers.fragment.IPopableFragment;
import fr.cesi.basecode.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkingFragment extends Fragment implements IPopableFragment {
    private static final String ARG_DURATION = "getDuration";
    private static final String ARG_DURATION_PAUSE = "getPause";
    private static final String ARG_NUMBER_REPETITION = "getRepetition";

    private int _getDuration;
    private int _getPause;
    private int _getRepetition;

    private Handler _handler;
    private int _duration;
    private int _duration_pause;


    private DonutProgress _working_progress;
    private DonutProgress _pause_progress;

    private ImageView _stop_image;
    private ImageView _start_image;

    private TextView _counter;
    private TextView _repetitions;


    public WorkingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WorkingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkingFragment newInstance(int getDuration, int getPause, int getRepetition) {
        WorkingFragment fragment = new WorkingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_DURATION, getDuration);
        args.putInt(ARG_DURATION_PAUSE, getPause);
        args.putInt(ARG_NUMBER_REPETITION, getRepetition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _duration = getArguments().getInt(ARG_DURATION);
            _duration_pause = getArguments().getInt(ARG_DURATION_PAUSE);
            _getRepetition = getArguments().getInt(ARG_NUMBER_REPETITION);
        }

        _duration *= 1000;
        _duration_pause *= 1000;

        _handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_working, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View main_button = view.findViewById(R.id.main_btn);

        _counter = (TextView) view.findViewById(R.id.counter);
        _repetitions = (TextView) view.findViewById(R.id.repetition);

        _start_image = (ImageView) view.findViewById(R.id.start_btn);
        _stop_image = (ImageView) view.findViewById(R.id.stop_btn);

        _pause_progress = (DonutProgress) view.findViewById(R.id.pause_progress);
        _working_progress = (DonutProgress) view.findViewById(R.id.working_progress);

        main_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_started == false) {
                    _started = true;
                    init();
                }
            }
        });


    }

    @Override
    public boolean hasParent() {
        return true;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    /*************************************************************************************
     *
     *
     *************************************************************************************/
    private static final String TYPE_DURATION = "TYPE_DURATION";
    private static final String TYPE_DURATION_PAUSE = "TYPE_DURATION_PAUSE";

    private double remaining_duration = 0;
    private int remaining_repetition = 0;
    private String _current_type;

    private boolean _started = false;


    private void init() {
        remaining_duration = _duration;
        _current_type = TYPE_DURATION;
        postStartLoop();
    }

    private void refreshUI() {
        //mettre le fonctionnement
        int remaining_seconds = (int) (remaining_duration / 1000);

        _counter.setText(""+remaining_seconds);
        if(TYPE_DURATION.equals(_current_type)) {
            _start_image.setVisibility(View.VISIBLE);
            _working_progress.setVisibility(View.VISIBLE);

            _stop_image.setVisibility(View.GONE);
            _pause_progress.setVisibility(View.GONE);

            _working_progress.setMax(_duration);
            _working_progress.setProgress(_duration - remaining_seconds);
        } else {
            _start_image.setVisibility(View.GONE);
            _working_progress.setVisibility(View.GONE);

            _stop_image.setVisibility(View.VISIBLE);
            _pause_progress.setVisibility(View.VISIBLE);

            _pause_progress.setMax(_duration_pause);
            _pause_progress.setProgress(_duration_pause - remaining_seconds);
        }
    }

    private void startLoop() {
        if(remaining_repetition >= 0 && remaining_duration >= 0) {
            refreshUI();
            remaining_duration -= 50;//ms

            if(remaining_duration <= 0) {
                if(_current_type == TYPE_DURATION) {
                    if(remaining_repetition > 0) {
                        remaining_repetition --;
                        _current_type = TYPE_DURATION_PAUSE;
                        remaining_duration = _duration_pause;
                    }
                } else { //_current_type == TYPE_DURATION_PAUSE
                    _current_type = TYPE_DURATION;
                    remaining_duration = _duration;
                }
            }

            postStartLoop();
        } else {
            _started = false;
            //FINISH
        }
    }

    private void postStartLoop() {
        _handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               startLoop();
            }
        }, 50);
    }
}

