package br.com.Oystr;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

/**
 *
 * @author Luan
 */
public class Control {

    //
    public static String[] analysisProcess(String url) {
        //string usada para retornar os dados para exibição
        String[] datas;

        try {
            datas = new String[5];
            //adiciona a URL no atributo @datas
            datas[0] = url;

            Document doc = Jsoup.connect(url).get();
            //adiciona o título da página no atributo @datas
            datas[2] = doc.head().selectFirst("title").text();

            //adiciona a versão do HTML no atributo @datas
            datas[1] = generateHtmlVersion(doc.childNode(0));

            //Analisa os links e verifica quantos são internos e quantos são externos
            Elements links = doc.body().select("[href]"); //encontra os links do corpo de texto
            int in, out;
            in = out = 0;
            //analisa os links
            for (Element link : links) {
                if (link.attr("href").contains("http")) { //caso tenha http então é um link externo
                    System.out.println("OUT " + link.attr("href"));
                    out++;
                } else {//se não é link interno
                    System.out.println("IN " + link.attr("href"));
                    in++;
                }
            }
            //adiona a quantidade de links internos e externos no atributo @datas
            datas[3] = String.valueOf(out);//adiciona link externo
            datas[4] = String.valueOf(in);//adiciona link interno
            return datas;

        } catch (IOException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        
    }

    //identifica a versão do HTML
    private static String generateHtmlVersion(Node node) {
        DocumentType documentType = (DocumentType) node;
        String htmlVersion = documentType.attr("publicid");
        return "".equals(htmlVersion) ? "HTML5" : htmlVersion;
    }

    public static Boolean validaURL(String u) {
        try {
            URL url = new URL(u);
            url.toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException ex) {
            return false;
        }

    }
}
