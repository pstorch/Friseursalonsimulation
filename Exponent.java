import java.util.Random;
import java.lang.Math;

class Exponent
{
static float expozufall (float ewert)
{
float expo;
float normrand;

zufall();
normrand=(float) (Math.random());
expo=(float) (-ewert*Math.log((double)normrand));
return(expo);
}

static float zufall()
{
return(0);
}

}
