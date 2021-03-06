package com.example.outfitplanner;

class ColorDictionary {
  public static String GetResult(String topColor, String bottomColor){
      String verdict = "";

      String[] neutral = {"antiquewhite", "beige", "floralwhite", "ghostwhite", "navajowhite", "whitesmoke", "white", "ivory", "mintcream", "lightgrey", "lightgray", "sandybrown", "tan", "aqua", "aquamarine", "darkgray", "darkgrey", "darkslategray", "darkslategrey", "dimgray", "dimgrey", "gray", "grey", "lightslategray", "lightslategrey", "slategray", "slategray", "darkkhaki", "midnightblue","khaki", "olivedrab", "olive", "navy", "brown", "rosybrown", "saddlebrown", "sandybrown", "black","bisque","blanchedalmond","burlywood","cornsilk","gainsboro","honeydew","lavender","lavenderblush","lemonchiffon","lightcyan","lightpink","lightsalmon","linen","mintcream","mistyrose","moccasin","oldlace","palegoldenrod","paleturquoise","papayawhip","peachpuff","seashell","silver","snow","pink","plum","thistle","wheat","mediumaquamarine","chocolate",""};
     
      String[] primary = {"darkred", "indianred", "mediumvioletred", "palevioletred", "red", "lightgoldenrodyellow", "lightyellow", "yellow", "aliceblue", "blue", "cadetblue", "cornflowerblue", "darkblue", "darkslateblue", "deepskyblue", "dodgerblue", "lightblue", "lightskyblue", "lightsteelblue", "mediumblue", "mediumslateblue",  "powderblue", "royalblue", "skyblue", "slateblue", "steelblue","crimson","cyan","darkcyan",""};

      String[] secondary = {"darkorange", "orange", "darkviolet", "violet", "darkgreen", "darkolivegreen", "darkseagreen", "forestgreen", "green", "lawngreen", "lightgreen", "lightseagreen", "lime", "limegreen", "mediumseagreen", "mediumspringgreen", "palegreen", "seagreen", "springgreen","chartreuse","darkgoldenrod","darkmagenta","darkorchid","darkturquoise","deeppink","fuchsia","hotpink","indigo","lightcoral","magenta","mediumorchid","mediumpurple","mediumturquoise","orchid","purple","turquoise"};

      String[] tertiary = {"greenyellow", "yellowgreen", "orangered", "blueviolet","coral","darksalmon","firebrick","gold","goldenrod","maroon","peru","salmon","sienna","teal","tomato",""};

      if((contains(neutral, bottomColor) && (contains(primary, topColor) || contains(secondary, topColor) || contains(tertiary, topColor) ||contains(neutral, topColor)))
              ||(contains(neutral, topColor) && (contains(primary, bottomColor) || contains(secondary, bottomColor) || contains(tertiary, bottomColor) ||contains(neutral, bottomColor)))
              || ((contains(primary, topColor) || contains(secondary, topColor)) && contains(tertiary, bottomColor))
              || ((contains(primary, bottomColor) || contains(secondary, bottomColor)) && contains(tertiary, topColor))
              ){
          verdict = "The colors match. You can wear this outfit.";
      }else{
          verdict = "The colors don't complement each other. Please try another pair!";
      }

     return verdict;
  }

  static boolean contains(String[] array, String color){
      for(String s:array){
          if(s.equals(color)){
              return true;
          }
      }
      return false;
  }
}