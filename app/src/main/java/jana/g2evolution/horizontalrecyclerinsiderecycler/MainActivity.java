package jana.g2evolution.horizontalrecyclerinsiderecycler;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;


import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    JSONParser jsonParser = new JSONParser();
    RecyclerView my_recycler_view;
    ArrayList<SectionDataModel> allSampleData;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        allSampleData = new ArrayList<SectionDataModel>();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle("G PlayStore");

        }


       // createDummyData();


        my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);

        my_recycler_view.setHasFixedSize(true);

        ConnectionDetector cd = new ConnectionDetector(context);
        if (cd.isConnectingToInternet()) {


        /*Downloading data from below url*/
           /* final String url = "http://kaffeecup.com/feader/feed_fetch.php";
            new AsyncHttpTask().execute(url);
*/
            // new LoadSpinnerdata().execute();

             new userdata().execute();



        } else {


            Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }



    }

    public void createDummyData() {
        for (int i = 1; i <= 5; i++) {

            SectionDataModel dm = new SectionDataModel();

            dm.setHeaderTitle("Section " + i);

            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
            for (int j = 0; j <= 5; j++) {
                singleItem.add(new SingleItemModel("Item " + j, "URL " + j));
            }

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);

        }
    }



    public class userdata extends AsyncTask<String, String, String> {
        String responce;
        String message;
        String headers;
        String childs;

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/

        }

        protected String doInBackground(String... args) {
            Integer result = 0;
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();

            Log.e("testing", "jsonParser startedkljhk");
            //userpramas.add(new BasicNameValuePair("feader_reg_id", id));
            //  Log.e("testing", "feader_reg_id" + id);

            JSONObject json = jsonParser.makeHttpRequest("http://www.ahilgroup.com/app/menu.php", "POST", userpramas);

            Log.e("testing", "jsonParser" + json);


            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            } else {
                Log.e("testing", "jon2222222222222");
                // DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    JSONObject response = new JSONObject(json.toString());

                    Log.e("testing", "jsonParser2222" + json);

                    //JSONObject jsonArray1 = new JSONObject(json.toString());
                    // Result = response.getString("status");
                    JSONArray posts = response.optJSONArray("categories");
                    Log.e("testing", "jsonParser3333" + posts);



              /* if (posts.equals(null)){
                   listDataHeader = new ArrayList<FeedHeader>();
                   listDataChild= new HashMap<String, FeedDetail>();
               }else{
                   listDataHeader.clear();
                   listDataChild.clear();
               }*/
            /*Initialize array if null*/
                  /*  if (null == listDataHeader || null == listDataChild) {
                        listDataHeader = new ArrayList<FeedHeader>();
                        // listDataChild = new ArrayList<FeedDetail>();
                        listDataChild = new HashMap<String, List<FeedDetail>>();
                    } else {
                        listDataHeader.clear();
                        listDataChild.clear();
                    }*/

                    if (posts == null) {
                        Log.e("testing", "jon11111111111111111");

                        //Toast.makeText(getContext(),"No data found",Toast.LENGTH_LONG).show();
                    } else {

                        Log.e("testing", "jon122222211");
                        Log.e("testing", "jsonParser4444" + posts);

                        for (int i = 0; i < posts.length(); i++) {
                            Log.e("testing", "" + posts);

                            Log.e("testing", "" + i);
                            JSONObject post = posts.optJSONObject(i);
                            // JSONArray posts2 = response.optJSONArray("categories");
                            Log.e("testng", "" + post);
                            headers = post.getString("cat_name");

                            Log.e("testing", "name is 11= " + post.getString("cat_name"));




                            String Title = post.getString("cat_name");

                            SectionDataModel dm = new SectionDataModel();

                            dm.setHeaderTitle(post.getString("cat_name"));

                            JSONArray posts2 = post.optJSONArray("categories");
                            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
                            for (int i1 = 0; i1 < posts2.length(); i1++) {
                                JSONObject post2 = posts2.optJSONObject(i1);

                                String Title2 = post2.getString("cat_name");
                                String Productid = post2.getString("category_id");
                                String Parentid = post2.getString("parent_id");
                                String Typename = post2.getString("type");

                                singleItem.add(new SingleItemModel(post2.getString("cat_name"), "URL " + i1));

                                //find the group position inside the list
                                //groupPosition = deptList.indexOf(headerInfo);
                            }

                            dm.setAllItemsInSection(singleItem);

                            allSampleData.add(dm);


                        }



                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return responce;
            }


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(context, allSampleData);

            my_recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

            my_recycler_view.setAdapter(adapter);

        }


    }

}