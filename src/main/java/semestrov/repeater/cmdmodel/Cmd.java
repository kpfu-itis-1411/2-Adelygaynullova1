package semestrov.repeater.cmdmodel;

public class Cmd {
    /*

    {"cmd":3, "otherClientName":"Kamil", "data":""}

    2. Получение списка пользователей
 *
 * 3. Передача данных другому клиенту
 *
 * 4. Завершение сессии
     */
    public int code;
    public String player_1;
    public String player_2;
    public int x;

    public int y;



    @Override
    public String toString() {
        return "Cmd{" +
                "code=" + code +
                ", player_1=" + player_1 +
                ", player_2='" + player_2 + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                '}';
    }
}
