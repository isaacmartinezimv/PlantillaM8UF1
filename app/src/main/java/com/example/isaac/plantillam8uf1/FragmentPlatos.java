package com.example.isaac.plantillam8uf1;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentPlatos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentPlatos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPlatos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //Variables de datos obtenidos de API
    ArrayList<PlatosModel> listaPlatos;

    //Variables de elementos en pantalla
    ProgressBar spinner;
    RecyclerView recyclerPlatos;

    //Varibales para la recyclerView
    PlatosAdapter adapter;

    public FragmentPlatos() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentPlatos newInstance(String param1, String param2) {
        FragmentPlatos fragment = new FragmentPlatos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_platos, container, false);

        //Creamos un array list vacio asociado a listaPlatos
        listaPlatos = new ArrayList<>();

        //Asociamos los elementos de la vista con sus IDs
        recyclerPlatos = (RecyclerView) view.findViewById(R.id.recyclerPlatosID);
        spinner = (ProgressBar) view.findViewById(R.id.progressBar2);

        //Nada mas iniciar la vista nos interesa que el spinner sea visible
        spinner.setVisibility(View.VISIBLE);

        //HAcemos llamada a API asi, Si añadimos esta parte de codigo nos dira que la clase HijoAPI no existe y que si queremos crearla.
        //La crearemos manualmente ahora despues y sera de tipo Async. En la segunda ñinea, ejecutaremos la clase con un parametro de tipo String que sera
        //la url de nuestra API

        HiloAPI hilo = new HiloAPI();
        hilo.execute("https://jdarestaurantapi.firebaseio.com/menu.json");

        //Configuramos la recyclerview añadiendole el layoutManager
        recyclerPlatos.setLayoutManager(new LinearLayoutManager(getContext()));

        //Creamos un adapter del tipo que hemos creado anteriormente y le pasamos la lista de platos,
        //este adapter que creamos se lo asignamos a nuestra recycler view
        adapter = new PlatosAdapter(listaPlatos);
        recyclerPlatos.setAdapter(adapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //Creamos la clase Asyncrona que lamaremos para ejecutar nuestra llamada a API, como en el caso del
    //Adapter, si definimos la classe unicamente como que extiende de AsyncTask, el linter de android nos indicara que añadamos el metodo doInBackground
    //dejamos que el linter lo añada e introducimos nuestro codigo.

    class HiloAPI extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            //Declaramos una varibale de tipo HHtpURLConection, una de tipo URL y una de tipo String
            HttpURLConnection connection;
            URL url;
            String result;
            result ="";

            //Intentamos realizar la conexion a API obteniendo el link que le hemos pasado a esta classe por parametro mediante strings[0]
            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();

                //Leemos los datos de entrada
                InputStream inputStream = connection.getInputStream();

                //hacemos un bucle para leer y guardar caracter a caracter los datos de entrada en nuestra variable "result"
                int data = inputStream.read();
                while(data != -1) {
                    result += (char) data;
                    data = inputStream.read();
                }

            } catch (Exception e){
                e.printStackTrace();
            }

            //Una vez tenemos todos los datos, los retornamos
            Log.i("RESULT", result);
            return result;
        }

        //Usamos el control + O y creamos la funcion onPostExecute, y cambiamos el nombre de la variable de entrada de "s" a "data"
        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);

            //Ahora es el momento de parsear los datos con nuestro objeto modelo
            try {
                //Declaramos un JSONObject y lo inicializamos con nuestros datos (data)
                JSONObject jsonObject = new JSONObject(data);

                //Declaramos un JSONArray. En este objeto introduciremos solo los datos correspondientes a los entrantes.
                JSONArray jsonArray = jsonObject.getJSONArray("entrantes");

                //Una vez tenemos los datos de los entrantes en un array, recorremos el array para extraer los datos en cada campo (nombre, ingredientes, precio)
                for(int i=0; i<jsonArray.length(); i++) {

                    //declaramos e instanciamos un nuevo objeto con el modelo de datos para ir rellenando en funcion de los datos que obtenemos
                    PlatosModel platos = new PlatosModel();

                    //Introducimos los datos del primer elemento del array en un JSONbject
                    JSONObject jsonitem = jsonArray.getJSONObject(i);

                    //Seteamos el nombre con el que corresponde al campo "nombre" del jsonitem
                    platos.setNombre(jsonitem.getString("nombre"));

                    //Seteamos los ingredientes en el campo correspondiente
                    platos.setIngredientes(jsonitem.getString("ingredientes"));

                    //Seteamos el precio en su campo
                    platos.setPrecio(jsonitem.getString("precio"));

                    //Escribimos en el log la respuesta (no necesario)
                    Log.i("Lista", platos.getNombre());

                    //Ahora que ya tenemos el objeto con el modelo de datos "rellenado", lo añadimos al array list global "listaPlatos"
                    //que pasaremos al adapter para que haga la recycler
                    listaPlatos.add(platos);

                } //Ceramos el loop

                //Declaramos un JSONArray. En este objeto introduciremos solo los datos correspondientes a los postres. Y seguimos el mismo proceso que antes
                JSONArray jsonArray2 = jsonObject.getJSONArray("postres");

                for(int i=0; i<jsonArray2.length(); i++) {
                    PlatosModel platos = new PlatosModel();

                    JSONObject jsonitem = jsonArray2.getJSONObject(i);
                    platos.setNombre(jsonitem.getString("nombre"));
                    platos.setIngredientes(jsonitem.getString("ingredientes"));
                    platos.setPrecio(jsonitem.getString("precio"));

                    listaPlatos.add(platos);
                } //Cerramos Loop

                //Declaramos un JSONArray para los platos principales y repetimos el processo
                JSONArray jsonArray3 = jsonObject.getJSONArray("principales");

                for(int i=0; i<jsonArray3.length(); i++) {
                    PlatosModel platos = new PlatosModel();

                    JSONObject jsonitem = jsonArray3.getJSONObject(i);
                    platos.setNombre(jsonitem.getString("nombre"));
                    platos.setIngredientes(jsonitem.getString("ingredientes"));
                    platos.setPrecio(jsonitem.getString("precio"));

                    listaPlatos.add(platos);

                } //Cerammos Loop



            } catch (Exception e) {
            e.printStackTrace();
            }


            //una vez hemos obtenido todos los datos, ocultamos el spinner y avisamos al adapter de la recycler para que pinte los cambios
            spinner.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
    }

}
