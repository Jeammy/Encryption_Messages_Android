package ca.csf.mobile1.tp2.model;

import android.os.AsyncTask;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/**
 * Created by Jeammy and Frédéric
 * on 28/03/17.
 */

public class AsyncTaskEncryptData extends AsyncTask<String,Void, String> {

    private final LinkedList<IListener> listeners= new LinkedList<>();
    private boolean isSuccesful = true;
    private boolean noIOException = true;
    private SubstitutionCypherKey substitution;
    private Exception exception;

    /**
     *
     * @param params params[0] est la clé d'encryption.
     *               params[1] est le texte à encrypter.
     *               param[2] encrypt ou decrypt pour savoir si on encrypte ou pas.
     * @return le texte encrypté.
     */
    @Override
    protected String doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(String.format("http://cypherkeys-acodebreak.rhcloud.com/substitution/key/%s.json",params[0]))
                .build();

        Response response = null;
        String returnString = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            exception = e;
            noIOException = false;
        }

        String json;
        if (response != null && response.isSuccessful()) {
            try {
                json = response.body().string();
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.addMixIn(SubstitutionCypherKey.class, SubstitutionCypherKeyMixIn.class);
                substitution = objectMapper.readValue(json, SubstitutionCypherKey.class);
            } catch (JsonParseException | JsonMappingException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                noIOException = false;
            }
            ComputeEncryption computeEncryption = new ComputeEncryption(substitution.getInputCharacters(),
                    substitution.getOutputCharacters(),
                    params[1]);
            if (params[2].equals("encrypt")) {
                returnString = computeEncryption.encrypt();
            } else if (params[2].equals("decrypt")){
                returnString = computeEncryption.decrypt();
            }
        }
        else{
            isSuccesful = false;
        }
        if (response != null) {
            response.close();
        }
        return returnString;
    }

    @Override
    protected void onPostExecute(final String result){
        if (isSuccesful && noIOException && exception == null) {
            for (IListener listener : listeners) {
                listener.onResponse(result);
            }
        }
        else{
            for (IListener listener : listeners) {
                listener.onException(exception);
            }
        }
        if (!isSuccesful){
            for (IListener listener : listeners){
                listener.errorIsNotSuccesful();
            }
        }
        if(!noIOException){
            for (IListener listener : listeners){
                listener.errorIOException();
            }
        }

    }

    public interface IListener{
        void onResponse(String response);
        void onException(Exception e);
        void errorIsNotSuccesful();
        void errorIOException();
    }

    public void addListener(IListener listener){
        listeners.add(listener);
    }
}
