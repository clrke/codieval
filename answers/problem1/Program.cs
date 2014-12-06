using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace HelloWorld
{
    class Program
    {
        static void Main(string[] args)
        {
            using (StreamReader reader = new StreamReader("input.txt"))
            {
                using (StreamWriter writer = new StreamWriter("output.txt"))
                {
                    string line;
                    while ((line = reader.ReadLine()) != null)
                    {
                        writer.WriteLine(String.Format("Hello {0}!", line));
                    }
                }
            }
        }
    }
}
