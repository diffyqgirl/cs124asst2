import java.io.*;
public class MakeInfile
{
    //usage: java MakeInfile n file.txt
    //outputs 2*n^2 integers, each on their own line, to infile.txt
    public static void main(String[] args) throws IOException
    {
        final int MIN = 0;
        final int MAX = 3;
        int n = Integer.parseInt(args[0]);
        File infile = new File(args[1]);
        FileWriter fw = new FileWriter(infile);
        PrintWriter pw = new PrintWriter(fw);
        for (int i = 0; i < 2*n*n; i++)
        {
            pw.println((int) (Math.random()*MAX) + MIN);
        }
        pw.close();
    }
}