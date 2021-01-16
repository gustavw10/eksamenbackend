package facades;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import utils.HttpUtils;

/**
 *
 * @author David
 */
public class FetchFacade {

    class FetchDefault implements Callable<String> {

        String url;

        FetchDefault(String url) {
            this.url = url;
        }

        @Override
        public String call() throws Exception {
            return HttpUtils.fetchData(url);
        }
    }

    public List<String> fetchParallel() throws InterruptedException, ExecutionException {
        String[] hostList = {"https://icanhazdadjoke.com",
            "https://some-random-api.ml/facts/fox",
            "https://swapi.dev/api/",
            "https://some-random-api.ml/facts/panda",
            "https://some-random-api.ml/facts/dog"};
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<String>> futureList = new ArrayList();
        List<String> result = new ArrayList();

        for (String url : hostList) {
            Callable<String> newUrl = new FetchDefault(url);
            Future future = executor.submit(newUrl);
            futureList.add(future);
        }

        for (Future<String> future : futureList) {
            result.add(future.get());
        }
        return result;
    }
}
