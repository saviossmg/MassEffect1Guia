package com.saviosvm.masseffect1guia.view.activity;

import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.saviosvm.masseffect1guia.R;
import com.saviosvm.masseffect1guia.controller.JogadorAglomeradoC;
import com.saviosvm.masseffect1guia.controller.JogadorC;
import com.saviosvm.masseffect1guia.controller.JogadorSistemaC;
import com.saviosvm.masseffect1guia.view.fragment.Checkin;
import com.saviosvm.masseffect1guia.view.fragment.Lista;
import com.saviosvm.masseffect1guia.view.fragment.Perfil;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {

    //referencias visuais
    private Toolbar vrToolbar;
    private TabLayout vrTablayout;
    private ViewPager vrViewpager;
    private int[] vrTabicons = {
            R.drawable.icon_home,
            R.drawable.icon_lista,
            R.drawable.icon_checkin
    };

    private static JogadorC jogadoBanco;
    private static int idJogador;

    private int id;

    private JogadorAglomeradoC jogAgloBanco;
    private JogadorSistemaC jogSistBanco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        jogAgloBanco = new JogadorAglomeradoC(getApplicationContext());
        jogSistBanco = new JogadorSistemaC(getApplicationContext());

        id = getIntent().getExtras().getInt("id");

        //referencia do banco
        Perfil.validaBanco(jogadoBanco,jogAgloBanco,jogSistBanco,id);
        Checkin.validaBanco(jogAgloBanco,jogSistBanco,id);
        Lista.validaBanco(jogAgloBanco,jogSistBanco,id);

        //referencias visuais adicionadas ao controlador da activity
        vrToolbar = (Toolbar) findViewById(R.id.menu_toolbar);
        setSupportActionBar(vrToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        vrViewpager = (ViewPager) findViewById(R.id.menu_viewpager);

        //adicionando conteudo a viewpager
        setupViewPager(vrViewpager);

        vrTablayout = (TabLayout) findViewById(R.id.menu_tabs);
        vrTablayout.setupWithViewPager(vrViewpager);

        //adicionando icones
        setupTabIcons();

    }

    //metodo que adicionará os icones as abas
    private void setupTabIcons() {
        /*
        vrTablayout.getTabAt(0).setIcon(vrTabicons[0]);
        vrTablayout.getTabAt(1).setIcon(vrTabicons[1]);
        vrTablayout.getTabAt(2).setIcon(vrTabicons[2]);
        */

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Perfil(), "Perfil");
        adapter.addFrag(new Lista(), "Lista");
        adapter.addFrag(new Checkin(), "Check-In");
        viewPager.setAdapter(adapter);
    }

    public interface FragmentLifecycle {
        public void onResumeFragment();
    }

    public static void validaBanco(JogadorC paramJogador, int paramId) {
        jogadoBanco = paramJogador;
        idJogador = paramId;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
            //return null;
        }
    }

}
