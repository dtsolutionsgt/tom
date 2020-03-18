package com.dts.classes.Mantenimientos.Empresa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeFont_detList {
    @ElementList(inline=true,required =  false)
    public List<clsBeFont_det> items = new List<clsBeFont_det>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<clsBeFont_det> iterator() {
            return null;
        }

        @Nullable
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(@Nullable T[] a) {
            return null;
        }

        @Override
        public boolean add(clsBeFont_det clsBeFont_det) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends clsBeFont_det> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends clsBeFont_det> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(@Nullable Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public clsBeFont_det get(int index) {
            return null;
        }

        @Override
        public clsBeFont_det set(int index, clsBeFont_det element) {
            return null;
        }

        @Override
        public void add(int index, clsBeFont_det element) {

        }

        @Override
        public clsBeFont_det remove(int index) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<clsBeFont_det> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<clsBeFont_det> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<clsBeFont_det> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
}