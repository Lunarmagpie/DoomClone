import os
import json
import sys
from PIL import Image


TEX_WIDTH = 16
TEX_HEIGHT = 16


def main():

    try:
        image_path = sys.argv[1]
        print(image_path)
    except IndexError:
        print("No file was provided.")
        return

    if not image_path.endswith(".png"):
        print("Image must be a png image")
        return

    img = Image.open(image_path).convert('RGBA')

    width, height = img.size
    img_colors = img.load()
    output = [0] * (width * (height-1) + width)

    for x in range(width):
        for y in range(height):
            r, g, b, a = img_colors[x, y]
            output[width * y + x] = (r << 16) + (g << 8) + b

    with open(f"assets/{image_path.split('.')[-2]}.rtx", "w") as file:
        file.write(json.dumps(output))


if __name__ == '__main__':
    main()
