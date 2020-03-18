package com.dts.classes.Mantenimientos.Producto.Producto_tipo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeProducto_tipoList {
    @ElementList(inline=true,required = false)
    public List<clsBeProducto_tipo> items = new List<clsBeProducto_tipo>() {
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
        public Iterator<clsBeProducto_tipo> iterator() {
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
        public boolean add(clsBeProducto_tipo clsBeProducto_tipo) {
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
        public boolean addAll(@NonNull Collection<? extends clsBeProducto_tipo> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends clsBeProducto_tipo> c) {
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
        public clsBeProducto_tipo get(int index) {
            return null;
        }

        @Override
        public clsBeProducto_tipo set(int index, clsBeProducto_tipo element) {
            return null;
        }

        @Override
        public void add(int index, clsBeProducto_tipo element) {

        }

        @Override
        public clsBeProducto_tipo remove(int index) {
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
        public ListIterator<clsBeProducto_tipo> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<clsBeProducto_tipo> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<clsBeProducto_tipo> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
}