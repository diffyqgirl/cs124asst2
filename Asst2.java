import java.util.Arrays;
public class Asst2
{
    static final int MIN = 0;
    static final int MAX = 2;
    public static void main(String[] args)
    {
        int n = Integer.parseInt(args[1]);
        randtests(n);
    }

    public static void randtests(int n)
    {
        int[][] a = new int[n][n];
        int[][] b = new int[n][n];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                a[i][j]= (int)(Math.random()*(MAX-MIN) + MIN);
                b[i][j]= (int)(Math.random()*(MAX-MIN) + MIN);
            }
        }
        int[][] c = multiply(a,b);
        //print2D(c);
    }

    public static int[][] multiply(int[][] a, int[][] b)
    {
        int[][] c = new int[a.length][a.length];
        for (int i = 0; i < a.length; i++)
        {
            for (int k = 0; k < a.length; k++)
            {
                for (int j = 0; j < a.length; j++)
                {
                    c[i][j] += a[i][k]*b[k][j];
                }
            }
        }
        return c;
    }

    // for testing
    public static void print2D(int[][] a)
    {
        for (int i = 0; i < a.length; i++)
        {
            System.out.println(Arrays.toString(a[i]));
        }
    }
}
