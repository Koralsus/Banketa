import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-home',
  imports: [CommonModule],
  templateUrl: './home.html',
  styleUrl: './home.scss',
  standalone: true
})
export class Home {
  @Input() changeView!: (view: 'home' | 'login' | 'register' | 'dashboard') => void;

    images = [
    'https://wonderfulengineering.com/wp-content/uploads/2016/11/sidewalk.jpg',
    'assets/carousel2.png',
    'assets/carousel3.png',
    'assets/carousel4.png'
  ];
  currentIndex = 0;
  intervalId?: any;

  ngOnInit() {
    this.intervalId = setInterval(() => {
      this.nextImage();
    }, 4000);
  }

  ngOnDestroy() {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }

  prevImage() {
    this.currentIndex = (this.currentIndex === 0) ? this.images.length - 1 : this.currentIndex - 1;
  }

  nextImage() {
    this.currentIndex = (this.currentIndex === this.images.length - 1) ? 0 : this.currentIndex + 1;
  }
}
