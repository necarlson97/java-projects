
import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import java.awt.Color;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mac
 */
public class Test extends SimpleApplication{
    
    public static void main(String[] args){
        Test test = new Test();
        test.start();
    }

    @Override
    public void simpleInitApp() {
        Box b = new Box((float).5, (float).1, (float).2);
        Geometry geom = new Geometry("Box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mat.setColor("Color", ColorRGBA.Red);
        mat.setTexture("ColorMap", assetManager.loadTexture("Textures/Fur.jpg"));
        geom.setMaterial(mat);
        rootNode.attachChild(geom);
        
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f).normalizeLocal());
        rootNode.addLight(sun);
    }
    
}
