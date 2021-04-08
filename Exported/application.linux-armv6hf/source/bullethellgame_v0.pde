Player p = new Player(1080/2, 650);

int bCount = 60;
Bullet[] bullets = new Bullet[200];

Button[] bt = new Button[5];

Screen s = new Screen();

int colorScheme = 1; 

color[] backColor = {#28334A, #2A2B2D, #FFDDE2, #695356};        // darkBlue, black, pink, darkBrown
color[] bulletColor = {#FBDE44, #D9514E, #FAA094, #ECB865};      // yellow, red, darkPink, orange
color[] playerColor = {#F65058, #2DA8D8, #008C76, #FBC9BE};      // red, lightBlue, darkGreen, lightPink
color[] lplayerColor = {#EA858A, #83CCE8, #9ED9CC, #DB7B65};     // lightRed, lightBlue2, lightGreen, pink 

boolean[] keys = new boolean[128];

Table scores;
TableRow row;

PFont f8Bit;

float score;
float highscore;
float lastScore;
float points;
String date = day()+"/"+month()+"/"+year()+" - "+hour()+":"+minute()+":"+second();
String state = "Homescreen";

boolean mPressed;
boolean pulse = true;
float index = 0;




void declareBullets()
{
  for(int i=0; i<bCount; i++)
  {
    bullets[i] = new Bullet(random(0, 20));
  }
}



void bullets()
{
  for(int i=0; i<bCount; i++)
  {
    bullets[i].display();
    bullets[i].respawn();
  }
}




void collision()
{
  float d;
  
  for(int i=0; i<=bCount-1; i++)
  {
    d = dist(p.x, p.y, bullets[i].x, bullets[i].y);
    
    if(d < p.size/2 + bullets[i].size/2)
    {
      saveScore();
      
      state = "Failed";
    }
  }
}




void displayScore()
{
  textFont(f8Bit);
  textSize(35);
  textAlign(CORNER);
  
  text("score: " + int(score), 10, 22);
}




void loadScore()
{
  scores = loadTable("data/scores.csv", "header");
  
  row = scores.getRow(0);
  highscore = row.getFloat("score");
  
  row = scores.getRow(1);
  points = row.getFloat("points");
  
  row = scores.getRow(1);
  lastScore = row.getFloat("score");
}




void saveScore()
{
  points += score;
  
  row = scores.getRow(0);
    
  if(score > row.getFloat("score"))
  {
    row.setFloat("score", score);
    row.setString("date", date);
  }
  
  row = scores.getRow(1);
  row.setFloat("score", score);
  row.setString("date", date);
  row.setFloat("points", points);
  
  saveTable(scores, "data/scores.csv");
}




void play()
{
  background(backColor[colorScheme]);
  
  p.display();
  p.border();
  p.controll();
  
  bullets();
  
  collision();
  
  displayScore();
}




void setup()
{
  size(1080, 720, P2D);
  
  declareBullets();
  loadScore();
  
  f8Bit = createFont("8BitMadness.ttf", 300);
}




void draw()
{
  if(state == "Homescreen")
  {
    score = 0;
    declareBullets();
    
    loadScore();
    s.homescreen();
  }
  
  if(state == "Ingame")
  {
    play();
  }
  
  if(state == "Failed")
  {
    score = 0;
    declareBullets();
    
    loadScore();
    s.failscreen();
  }
}




void keyPressed()
{
  keys[key] = true;
}




void keyReleased()
{
  keys[key] = false;
}




void mousePressed()
{
  if(bt[1].touching)
  {
    if(colorScheme < backColor.length-1)
      {
        colorScheme++;
      }
      else
      {
        colorScheme = 0;
      }
  }
  
  if(bt[4].touching)
  {
    if(bCount < 200)
      {
        bCount += 10;
      }
      else
      {
        bCount = 30;
      }
  }
}




/* 
Namensidee: CircleHell
*/
