package nsu.Lab1;

import org.jetbrains.annotations.NotNull;

public class CryptoDeCoder {

  public static void main(String[] args) {
    String encryptedText =
        "ареярфоеооуыоолкдри.вда.авмзвсобиопренеещцнГцоотрегбипюурсниаядвкр)лпмнве"
            + "м,л;ехлробидмгвнзовзиссоанмезказареяефйтрсобилбааоНатткатралуиуиоецпдттсбяий(ооцутэ)щреецп"
            + "ядесоотеенлоНиврИонмнчниаючаоесецпчвеывходоинвниыцнотавинов(ноитагиеиртотхвнолаолздоннарш"
            + "аадйкте.кносаецпяемзчечаоуоптиы;ябчоиьнтаавеваянн:легноздбонянткртвненнселааоьео«ытеонто»"
            + "окниинояиосо;еннхинфетаутегйтцмносбюробирувевлооза;(бин(ндиинояньрвбннбНйегсыеб,птыунос:"
            + "еннгоиноийиокянмаэодиионвкр.аревощотамнванкдосноие)иостладоолдоянажгниЛогевмяннснианаиос"
            + "ыненлегколоосаоорниипфйтосечоеитил)яочовейтУноспинстроираилоуйтасромпфйтоинообносс;ертхи"
            + "е(л)е;ябоаооВвмзхйеианонкциаорлысея.пфйтоеотоагяацсьрдуичоириробибноспефйтсозмрНзнвеияор"
            + "имнмнслаооосниибоиирнкоейуатниаееруггежгиьвийес.гороаиостецпбснианачиэсрорсннозпиииунуно"
            + "стоиурдцолпеиомрилосвмзваегнмвчсвнеиссвНсазОдлаолпреуцемзадсиинУиомрхлаозл,чотиостелоо;ио"
            + "оналевеасиь";

    String keyWord = "ЛОБАЧЕВСКИЙ";
    System.out.println(decodeMessage(encryptedText, keyWord));
  }

  private static int[] getOrderByKey(@NotNull String theKey) {
    String key = theKey.toLowerCase();
    int[] order = new int[key.length()];
    int idx = 0;
    char currentMax = key.charAt(idx);
    int num = key.length() - 1;

    for (int i = 0; i < key.length(); i++) {
      if (key.charAt(i) >= currentMax) {
        currentMax = key.charAt(i);
        idx = i;
      }
    }
    order[idx] = num--;

    for (int i = 0; i < key.length() - 1; i++) {
      int maxIdx = 0;
      char max = 'а';
      for (int j = 0; j < key.length(); j++) {
        if (key.charAt(j) >= max && order[j] == 0) {
          max = key.charAt(j);
          maxIdx = j;
        }
      }
      idx = maxIdx;
      order[idx] = num--;
    }
    return order;
  }

  private static String decodeMessage(String message, String key) {
    StringBuilder result = new StringBuilder();
    int[] order = getOrderByKey(key);
    int[] reverseOrder = new int[key.length()];
    for (int i = 0; i < key.length(); i++) {
      reverseOrder[order[i]] = i;
    }

    int[] sum = new int[key.length()];
    int rowsInFullColumn = (int) Math.ceil((float) message.length() / key.length());
    int fullColumnsNum = key.length() - (rowsInFullColumn * key.length() - message.length());

    for (int i = 1; i < key.length(); i++) {
      if (reverseOrder[i - 1] < fullColumnsNum) {
        sum[i] = sum[i - 1] + rowsInFullColumn;
      } else {
        sum[i] = sum[i - 1] + rowsInFullColumn - 1;
      }
    }

    for (int i = 0; i < rowsInFullColumn; i++) {
      for (int j = 0; j < key.length(); j++) {
        if (i == rowsInFullColumn - 1 && j >= fullColumnsNum) {
          continue;
        }
        int idx = sum[order[j]] + i;
        if (idx < message.length()) {
          result.append(message.charAt(idx));
        }
      }
    }
    return result.toString();
  }
}
