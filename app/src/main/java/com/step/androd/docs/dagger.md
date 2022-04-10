
```
public class MainActivity extends AppCompatActivity implements MainContract.View {
    @Inject
    MainPresenter mainPresenter;
    ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
        //调用Presenter方法加载数据
         mainPresenter.loadData();

         ...
    }

}

public class MainPresenter {
    //MainContract是个接口，View是他的内部接口，这里看做View接口即可
    private MainContract.View mView;

    @Inject
    MainPresenter(MainContract.View view) {
        mView = view;
    }
    public void loadData() {
        //调用model层方法，加载数据
        ...
        //回调方法成功时
        mView.updateUI();
    }

@Module
public class MainModule {
    private final MainContract.View mView;

    public MainModule(MainContract.View view) {
        mView = view;
    }

    @Provides
    MainView provideMainView() {
        return mView;
    }
}

@Component(modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
```