# MuLightDemo
When i got this project,i found three mainly functions.Then i decided how to implement these functions as below.
Function1: make one action of taking photo to a image entity,  and restore it also read it.

Solution: create a image entity, create a sqlite db  to restore one image and query list.


Function2: take photo.

Solution: use intent to open system camera, notice that app need CAMERA and EXTERNAL STORAGE permission whatever mobile's sdk is above or below Android M.


Function3: show image list.

Solution: use recylerview to show list. use Glide to show each image which provider image cache to improve list performance. And Glide defaultly load image in the way of imageView's size, which meet the thumbnail requirement.

After solution made, it came out three activity for me. one is MainActivity which provider taking picture button and viewing list button. the other is ImageListActivity which show list. the last is ImageViewActivity which show the full image.

For the time limited and the project is juset a small demo. There are some points to impoved.
Some are small details:
1.When user input a name, i should ensure this name is not used, or new file will replace the old file of the same name.

2.Image's name need some validate judgement, like symbol, enter.

3.Add some popular gesture for big image preview, like double tap for scale, multi-finger for drag and scale.

Some are large structure:
1.Create a delegate class to start MainActivity, delegate expose some functions for developer, like modify restore path, modify grid column count, etc. This make the project become a module.

2.Add more function in sqlite db to cover other requirement, like query one image, use modified sort, delete image.

3.Make db cover more media type, put taking photo into a class which implement abstract class, which describe a action to handle several kind of medias include image, make the grid'adapter more abstract to meet other media.
