package in.cubestack.material.androidmaterial.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import in.cubestack.android.lib.storm.service.asyc.StormCallBack;
import in.cubestack.material.androidmaterial.R;
import in.cubestack.material.androidmaterial.model.Meaning;
import in.cubestack.material.androidmaterial.model.Sentence;
import in.cubestack.material.androidmaterial.model.WordList;
import in.cubestack.material.androidmaterial.util.UiUtils;

/**
 * Created by supal on 25/9/15.
 */
public class AddWorkActivity extends AbstractCubeStackActivity {

    private boolean exit = false;

    private ActionBarDrawerToggle drawerToggle;

    @Bind(R.id.view_nav_drawer)
    DrawerLayout drawerLayout;

    @Bind(R.id.view_nav)
    NavigationView navigationView;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Bind(R.id.edit_meanings)
    EditText meaning;

    @Bind(R.id.edit_usage)
    EditText usage;

    @Bind(R.id.edit_word)
    EditText word;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNavDrawer();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
initSave();
            }
        });
    }

    private void initSave() {
        if(TextUtils.isEmpty(word.getText())) {
            toast("Please provide new work");
            wobble(word);
            return;
        }

        if(TextUtils.isEmpty(meaning.getText())) {
            toast("Please provide meaning");
            wobble(meaning);
            return;
        }

        if(TextUtils.isEmpty(usage.getText())) {
            toast("Please provide Usages");
            wobble(usage);
            return;
        }


        WordList wordList = new WordList();
        wordList.setWord(word.getText().toString());

        String meanings = meaning.getText().toString();

        List<Meaning> meaningsList = new LinkedList<>();

            for(String str: meanings.split("\n")) {
                Meaning m = new Meaning();
                m.setMeaning(str);
                meaningsList.add(m);
            }


        List<Sentence> sentenceList = new LinkedList<>();

        for(String str: usage.getText().toString().split("\n")) {
            Sentence s = new Sentence();
            s.setSentence(str);
            sentenceList.add(s);
        }
        wordList.setMeanings(meaningsList);
        wordList.setSentences(sentenceList);

        final long startTime = System.currentTimeMillis();

        MainApplication.service().save(wordList, new StormCallBack<WordList>() {
            @Override
            public void onSave(WordList entity) {

                toast("Saved Successfully in " + (System.currentTimeMillis() - startTime) +" ms");
                finish();
            }

            @Override
            public void onError(Throwable throwable) {
            }
        });
    }


    @Override
    public void finish() {
        Intent resultIntent = getIntent();
        resultIntent.putExtra("success", "true");
        setResult(RESULT_OK, resultIntent);
        super.finish();
    }


    private void initNavDrawer() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.setDrawerListener(drawerToggle);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menuAdd:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.menuDeck:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        //TODO
                        break;
                    case R.id.menuFavorites:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        //TODO
                        break;
                    case R.id.menuPractices:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        //TODO
                        break;
                    case R.id.menuAboutUs:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        //TODO
                        break;
                    case R.id.menuLegal:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        //TODO
                        break;
                }
                return false;
            }
        });
    }


    @Override
    Drawable getToolBarIcon() {
        return UiUtils.getDrawable(this, R.drawable.ic_close_white);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_add_word;
    }
}
