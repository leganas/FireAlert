package by.legan.android.firealert.utils;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FragmentUtils {

    /**
     * Binding Fragment To ContentFrame
     * @param fragmentManager
     * @param id - ContentFrame
     */
    public static void bindingFragmentToContentFrame(FragmentManager fragmentManager, Fragment fr_bind, @IdRes int id) {
        // Ищем фрагмент (это если после поворота то он будет уже)
        Fragment fragment = fragmentManager.findFragmentById(id);
        // Если не нашли то создаём новый
        if (fragment == null) {
            fragment = fr_bind;
            fragmentManager.beginTransaction()
                    .add(id,fragment)
                    .commit();
        }
    }
}
